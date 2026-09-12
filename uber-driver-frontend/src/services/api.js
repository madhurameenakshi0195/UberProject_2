const API_URL = "http://localhost:8080";

export async function driverLogin(email, password) {
    const response = await fetch(`${API_URL}/drivers/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            email,
            password,
        }),
    });

    if (!response.ok) {
        throw new Error("Login failed");
    }

    return response.json();
}

export async function driverRegister(driverData) {
    const response = await fetch(`${API_URL}/drivers/register`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(driverData),
    });

    if (!response.ok) {
        throw new Error("Registration failed");
    }

    return response.json();
}

export async function updateDriverAvailability(driverId, available) {

    const token = localStorage.getItem("driverToken");

    const response = await fetch(
        `${API_URL}/drivers/availability/${driverId}?available=${available}`,
        {
            method: "PUT",

            headers: {
                "Authorization": `Bearer ${token}`,
            },
        }
    );

    if (!response.ok) {
        throw new Error("Failed to update availability");
    }

    return response.text();
}

export async function updateDriverLocation(
    driverId,
    latitude,
    longitude
) {

    const token = localStorage.getItem("driverToken");

    const response = await fetch(
        `${API_URL}/drivers/location/${driverId}`,
        {
            method: "PUT",

            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`,
            },

            body: JSON.stringify({
                latitude: latitude,
                longitude: longitude,
            }),
        }
    );

    if (!response.ok) {
        throw new Error("Failed to update location");
    }

    return response.text();
}
export async function userRegister(userData) {

    const response = await fetch(`${API_URL}/auth/register`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(userData),
    });

    const message = await response.text();

    if (!response.ok) {
        throw new Error(message);
    }

    return message;
}

export async function userLogin(email, password) {

    const response = await fetch(`${API_URL}/auth/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            email,
            password,
        }),
    });

    if (!response.ok) {
        throw new Error("Login failed");
    }

    return response.json();
}
export async function getAvailableDrivers() {

    const token = localStorage.getItem("userToken");

    const response = await fetch(
        `${API_URL}/drivers/available`,
        {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
            },
        }
    );

    if (!response.ok) {
        throw new Error("Failed to get available drivers");
    }

    return response.json();
}

export async function requestRide(rideData) {

    const token = localStorage.getItem("userToken");

    const response = await fetch(
        `${API_URL}/rides/request`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`,
            },
            body: JSON.stringify(rideData),
        }
    );

    if (!response.ok) {
        const message = await response.text();
        throw new Error(message || "Failed to request ride");
    }

    return response.json();
}

export async function getCurrentDriverRide(driverId) {
    const token = localStorage.getItem("driverToken");

    const response = await fetch(
        `${API_URL}/rides/driver/${driverId}/current`,
        {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
            },
        }
    );

    if (!response.ok) {
        throw new Error("Failed to get current ride");
    }

    return response.json();
}


export async function acceptRide(rideId, driverId) {
    const token = localStorage.getItem("driverToken");

    const response = await fetch(
        `${API_URL}/rides/${rideId}/accept/${driverId}`,
        {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`,
            },
        }
    );

    if (!response.ok) {
        const message = await response.text();
        throw new Error(message || "Failed to accept ride");
    }

    return response.json();
}


export async function startRide(rideId) {
    const token = localStorage.getItem("driverToken");

    const response = await fetch(
        `${API_URL}/rides/${rideId}/start`,
        {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`,
            },
        }
    );

    if (!response.ok) {
        const message = await response.text();
        throw new Error(message || "Failed to start ride");
    }

    return response.json();
}


export async function completeRide(rideId) {
    const token = localStorage.getItem("driverToken");

    const response = await fetch(
        `${API_URL}/rides/${rideId}/complete`,
        {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`,
            },
        }
    );

    if (!response.ok) {
        const message = await response.text();
        throw new Error(message || "Failed to complete ride");
    }

    return response.json();
}
export async function getRide(rideId) {

    const token = localStorage.getItem("userToken");

    const response = await fetch(
        `${API_URL}/rides/${rideId}`,
        {
            method: "GET",

            headers: {
                "Authorization": `Bearer ${token}`,
            },
        }
    );

    if (!response.ok) {

        const message = await response.text();

        console.error(
            "GET RIDE ERROR:",
            response.status,
            message
        );

        throw new Error(
            message || `Failed to get ride (${response.status})`
        );
    }

    const data = await response.json();

    console.log(
        "USER RECEIVED RIDE:",
        data
    );

    return data;
}