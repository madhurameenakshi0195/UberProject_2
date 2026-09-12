import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import {
    getAvailableDrivers,
    requestRide,
    getRide,
} from "../services/api";

import RideMap from "../components/RideMap";


function UserDashboard() {

    const navigate = useNavigate();

    const userName =
        localStorage.getItem("userName");


    const [userLocation, setUserLocation] =
        useState(null);

    const [destination, setDestination] =
        useState(null);

    const [drivers, setDrivers] =
        useState([]);

    const [ride, setRide] =
        useState(null);

    const [loading, setLoading] =
        useState(false);

    useEffect(() => {

        if (!ride?.id) {
            return;
        }

        console.log(
            "Starting ride status polling for ride:",
            ride.id
        );

        const interval = setInterval(async () => {

            try {

                const updatedRide =
                    await getRide(ride.id);

                console.log(
                    "Ride status:",
                    updatedRide.status
                );

                setRide(updatedRide);

                if (
                    updatedRide.status === "COMPLETED"
                ) {

                    clearInterval(interval);

                }

            } catch (error) {

                console.error(
                    "Ride status update error:",
                    error
                );

            }

        }, 3000);

        return () => {

            console.log(
                "Stopping ride status polling"
            );

            clearInterval(interval);

        };

    }, [ride?.id]);
    /*
     * GET USER LOCATION
     */

    useEffect(() => {

        navigator.geolocation.getCurrentPosition(

            (position) => {

                setUserLocation({

                    lat: position.coords.latitude,

                    lng: position.coords.longitude,

                });

            },

            (error) => {

                console.error(
                    "Location error:",
                    error
                );

                alert(
                    "Please allow location access."
                );

            }

        );

    }, []);


    /*
     * GET AVAILABLE DRIVERS
     */

    useEffect(() => {

        async function loadDrivers() {

            try {

                const data =
                    await getAvailableDrivers();

                setDrivers(data);

            } catch (error) {

                console.error(
                    "Driver loading error:",
                    error
                );

            }

        }

        loadDrivers();

    }, []);


    /*
     * DESTINATION SELECTED ON MAP
     */

    function handleDestinationSelect(location) {

        setDestination(location);

        setRide(null);

    }


    /*
     * REQUEST RIDE
     */

    async function handleRequestRide() {

        if (!userLocation) {

            alert(
                "Waiting for your location..."
            );

            return;

        }


        if (!destination) {

            alert(
                "Click on the map to choose your destination."
            );

            return;

        }


        setLoading(true);


        try {

            const rideData = {

                pickupLat:
                userLocation.lat,

                pickupLng:
                userLocation.lng,

                dropLat:
                destination.lat,

                dropLng:
                destination.lng,

            };


            const data =
                await requestRide(rideData);


            setRide(data);


            /*
             * Refresh available drivers
             */

            const updatedDrivers =
                await getAvailableDrivers();

            setDrivers(updatedDrivers);


        } catch (error) {

            console.error(error);

            alert(error.message);

        } finally {

            setLoading(false);

        }

    }


    /*
     * LOGOUT
     */

    function handleLogout() {

        localStorage.removeItem(
            "userToken"
        );

        localStorage.removeItem(
            "userId"
        );

        localStorage.removeItem(
            "userName"
        );

        localStorage.removeItem(
            "userEmail"
        );

        navigate("/login");

    }


    return (

        <div className="user-dashboard">


            {/* HEADER */}



            <header className="dashboard-header">

                <div className="uber-logo">
                    UBER
                </div>


                <div className="user-info">

                    <span>
                        Hi, {userName}
                    </span>

                    <button
                        onClick={handleLogout}
                    >
                        Logout
                    </button>

                </div>

            </header>


            {/* MAP */}

            <div className="map-container">

                <RideMap

                    userLocation={
                        userLocation
                    }

                    destination={
                        destination
                    }

                    drivers={
                        drivers
                    }

                    onDestinationSelect={
                        handleDestinationSelect
                    }

                />

            </div>


            {/* RIDE PANEL */}

            <div className="ride-panel">

                <h2>
                    Where are you going?
                </h2>


                <div className="location-row">

                    <span className="location-dot">
                        📍
                    </span>

                    <div>

                        <small>
                            Pickup
                        </small>

                        <p>
                            {userLocation
                                ? "Current location"
                                : "Getting location..."}
                        </p>

                    </div>

                </div>


                <div className="location-row">

                    <span className="location-dot">
                        📍
                    </span>

                    <div>

                        <small>
                            Destination
                        </small>

                        <p>

                            {destination
                                ? `${destination.lat.toFixed(5)}, ${destination.lng.toFixed(5)}`
                                : "Click on the map"}

                        </p>

                    </div>

                </div>


                <button
                    className="request-ride-button"
                    onClick={handleRequestRide}
                    disabled={loading}
                >

                    {loading
                        ? "Finding a driver..."
                        : "Request a ride"}

                </button>


                {/* RIDE RESULT */}

                {ride && (

                    <div className="ride-result">


                        <p>
                            <strong>
                                Status:
                            </strong>{" "}
                            {ride.status}
                        </p>


                        <p>
                            <strong>
                                Fare:
                            </strong>{" "}
                            ₹{ride.fare}
                        </p>


                        {ride.driver && (

                            <>

                                <p>
                                    <strong>
                                        Driver:
                                    </strong>{" "}
                                    {ride.driver.name}
                                </p>

                                <p>
                                    <strong>
                                        Vehicle:
                                    </strong>{" "}
                                    {ride.driver.vehicleModel}
                                </p>

                                <p>
                                    <strong>
                                        Number:
                                    </strong>{" "}
                                    {ride.driver.vehicleNumber}
                                </p>

                            </>

                        )}
                        <RideMap
                            userLocation={userLocation}
                            destination={destination}
                            drivers={drivers}
                            onDestinationSelect={handleDestinationSelect}
                        />

                    </div>

                )}

            </div>

        </div>

    );

}


export default UserDashboard;