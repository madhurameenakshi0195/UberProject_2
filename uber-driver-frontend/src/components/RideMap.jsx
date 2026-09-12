import {
    MapContainer,
    TileLayer,
    Marker,
    Popup,
    useMapEvents,
} from "react-leaflet";

import "leaflet/dist/leaflet.css";

import L from "leaflet";

import markerIcon from "leaflet/dist/images/marker-icon.png";
import markerShadow from "leaflet/dist/images/marker-shadow.png";


const userIcon = L.icon({
    iconUrl: markerIcon,
    shadowUrl: markerShadow,
    iconSize: [25, 41],
    iconAnchor: [12, 41],
});


function MapClickHandler({ onDestinationSelect }) {

    useMapEvents({

        click(event) {

            onDestinationSelect({
                lat: event.latlng.lat,
                lng: event.latlng.lng,
            });

        },

    });

    return null;
}


function RideMap({
                     userLocation,
                     destination,
                     drivers,
                     onDestinationSelect,
                 }) {

    if (!userLocation) {
        return (
            <div className="map-loading">
                Getting your location...
            </div>
        );
    }

    return (

        <MapContainer
            center={[
                userLocation.lat,
                userLocation.lng,
            ]}
            zoom={15}
            className="ride-map"
        >

            <TileLayer
                attribution='&copy; OpenStreetMap contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />


            <MapClickHandler
                onDestinationSelect={onDestinationSelect}
            />


            {/* USER */}

            <Marker
                position={[
                    userLocation.lat,
                    userLocation.lng,
                ]}
                icon={userIcon}
            >

                <Popup>
                    You nigga here
                </Popup>

            </Marker>


            {/* DESTINATION */}

            {destination && (

                <Marker
                    position={[
                        destination.lat,
                        destination.lng,
                    ]}
                    icon={userIcon}
                >

                    <Popup>
                        Destination
                    </Popup>

                </Marker>

            )}


            {/* AVAILABLE DRIVERS */}

            {drivers.map((driver) => {

                if (!driver.driverLocation) {
                    return null;
                }

                return (

                    <Marker
                        key={driver.id}
                        position={[
                            driver.driverLocation.latitude,
                            driver.driverLocation.longitude,
                        ]}
                        icon={userIcon}
                    >

                        <Popup>

                            🚗 {driver.name}

                            <br />

                            {driver.vehicleModel}

                            <br />

                            {driver.vehicleNumber}

                        </Popup>

                    </Marker>

                );

            })}

        </MapContainer>
    );
}

export default RideMap;