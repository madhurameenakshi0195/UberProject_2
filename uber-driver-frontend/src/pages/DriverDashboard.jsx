import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import {
    getCurrentDriverRide,
    acceptRide,
    startRide,
    completeRide,
    updateDriverLocation,
    updateDriverAvailability,
} from "../services/api";


function DriverDashboard() {

    const navigate = useNavigate();

    const driverId = localStorage.getItem("driverId");
    const driverName = localStorage.getItem("driverName");
    const vehicleNumber = localStorage.getItem("vehicleNumber");
    const vehicleModel = localStorage.getItem("vehicleModel");

    const [ride, setRide] = useState(null);
    const [loading, setLoading] = useState(false);

    const [available, setAvailable] = useState(
        localStorage.getItem("driverAvailable") === "true"
    );

    const [location, setLocation] = useState(null);

    async function loadRide() {
        try {

            const data = await getCurrentDriverRide(driverId);

            setRide(data);

        } catch (error) {

            console.error("Ride loading error:", error);

        }
    }


    useEffect(() => {

        if (!driverId) return;

        if (!navigator.geolocation) {
            alert("Geolocation is not supported by your browser.");
            return;
        }

        const watchId = navigator.geolocation.watchPosition(
            async (position) => {

                const latitude = position.coords.latitude;
                const longitude = position.coords.longitude;

                setLocation({
                    latitude,
                    longitude,
                });

                try {

                    await updateDriverLocation(
                        driverId,
                        latitude,
                        longitude
                    );

                    console.log(
                        "Driver location updated:",
                        latitude,
                        longitude
                    );

                } catch (error) {

                    console.error(
                        "Location update failed:",
                        error
                    );

                }
            },

            (error) => {

                console.error(
                    "Location permission/error:",
                    error
                );

                alert(
                    "Please allow location access for the driver."
                );
            },

            {
                enableHighAccuracy: true,
                maximumAge: 5000,
                timeout: 10000,
            }
        );

        return () => {
            navigator.geolocation.clearWatch(watchId);
        };

    }, [driverId]);

    useEffect(() => {

        loadRide();

        const interval = setInterval(() => {
            loadRide();
        }, 3000);

        return () => clearInterval(interval);

    }, []);

    async function handleAccept() {

        if (!ride) return;

        setLoading(true);

        try {

            const updatedRide = await acceptRide(
                ride.id,
                driverId
            );

            setRide(updatedRide);

        } catch (error) {

            alert(error.message);

        } finally {

            setLoading(false);

        }
    }

    async function handleStart() {

        if (!ride) return;

        setLoading(true);

        try {

            const updatedRide = await startRide(ride.id);

            setRide(updatedRide);

        } catch (error) {

            alert(error.message);

        } finally {

            setLoading(false);

        }
    }

    async function handleComplete() {

        if (!ride) return;

        setLoading(true);

        try {

            const updatedRide = await completeRide(ride.id);

            setRide(null);

        } catch (error) {

            alert(error.message);

        } finally {

            setLoading(false);

        }
    }


    async function handleAvailability() {

        try {

            const newAvailability = !available;

            await updateDriverAvailability(
                driverId,
                newAvailability
            );

            setAvailable(newAvailability);

            localStorage.setItem(
                "driverAvailable",
                newAvailability
            );

        } catch (error) {

            alert(error.message);

        }
    }


    function handleLogout() {

        localStorage.removeItem("driverToken");
        localStorage.removeItem("driverId");
        localStorage.removeItem("driverName");
        localStorage.removeItem("vehicleNumber");
        localStorage.removeItem("vehicleModel");
        localStorage.removeItem("driverAvailable");

        navigate("/driver/login");
    }

    return (
        <div className="driver-dashboard">

            <header className="driver-header">

                <div className="uber-logo">
                    UBER
                </div>

                <div className="driver-info">

                    <span>
                        Hi, {driverName}
                    </span>

                    <button onClick={handleLogout}>
                        Logout
                    </button>

                </div>

            </header>


            <main className="driver-main">

                <div className="driver-profile-card">

                    <h2>
                        Driver Dashboard
                    </h2>

                    <p>
                        <strong>Name:</strong>{" "}
                        {driverName}
                    </p>

                    <p>
                        <strong>Vehicle:</strong>{" "}
                        {vehicleModel}
                    </p>

                    <p>
                        <strong>Vehicle Number:</strong>{" "}
                        {vehicleNumber}
                    </p>

                    <div className="availability-section">

                        <p>
                            <strong>Status:</strong>{" "}
                            {available ? "🟢 Online" : "🔴 Offline"}
                        </p>

                        <button
                            onClick={handleAvailability}
                            className="availability-button"
                        >
                            {available
                                ? "Go Offline"
                                : "Go Online"}
                        </button>

                    </div>

                    {location && (
                        <p>
                            <strong>Location:</strong>{" "}
                            {location.latitude.toFixed(5)},
                            {" "}
                            {location.longitude.toFixed(5)}
                        </p>
                    )}

                </div>


                {!ride && (

                    <div className="waiting-card">

                        <h2>
                            🚗 Waiting for rides
                        </h2>

                        <p>
                            You are online and waiting
                            for a ride request.
                        </p>

                    </div>

                )}


                {ride && (

                    <div className="ride-request-card">

                        <h2>
                            🚨 New Ride Request
                        </h2>

                        <div className="ride-details">

                            <p>
                                <strong>Ride ID:</strong>{" "}
                                {ride.id}
                            </p>

                            <p>
                                <strong>Pickup:</strong>{" "}
                                {ride.pickupLat},
                                {" "}
                                {ride.pickupLng}
                            </p>

                            <p>
                                <strong>Destination:</strong>{" "}
                                {ride.dropLat},
                                {" "}
                                {ride.dropLng}
                            </p>

                            <p>
                                <strong>Fare:</strong>{" "}
                                ₹{ride.fare}
                            </p>

                            <p>
                                <strong>Status:</strong>{" "}
                                {ride.status}
                            </p>

                        </div>


                        {ride.status === "DRIVER_ASSIGNED" && (

                            <button
                                className="accept-ride-button"
                                onClick={handleAccept}
                                disabled={loading}
                            >
                                {loading
                                    ? "Accepting..."
                                    : "Accept Ride"}
                            </button>

                        )}


                        {ride.status === "ACCEPTED" && (

                            <button
                                className="start-ride-button"
                                onClick={handleStart}
                                disabled={loading}
                            >
                                {loading
                                    ? "Starting..."
                                    : "Start Ride"}
                            </button>

                        )}


                        {ride.status === "STARTED" && (

                            <button
                                className="complete-ride-button"
                                onClick={handleComplete}
                                disabled={loading}
                            >
                                {loading
                                    ? "Completing..."
                                    : "Complete Ride"}
                            </button>

                        )}

                    </div>

                )}

            </main>

        </div>
    );
}

export default DriverDashboard;