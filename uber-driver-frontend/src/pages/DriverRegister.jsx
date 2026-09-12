import { useState } from "react";
import { driverRegister } from "../services/api";

function DriverRegister() {

    const [form, setForm] = useState({
        name: "",
        email: "",
        password: "",
        vehicleNumber: "",
        vehicleModel: "",
    });

    const [message, setMessage] = useState("");

    function handleChange(event) {
        setForm({
            ...form,
            [event.target.name]: event.target.value,
        });
    }

    async function handleSubmit(event) {
        event.preventDefault();

        try {
            await driverRegister(form);

            setMessage("Driver registered successfully!");

        } catch (error) {
            setMessage(error.message);
        }
    }

    return (
        <div className="driver-register-page">

            <div className="driver-register-card">

                <div className="register-logo">
                    UBER
                </div>

                <h1>Become a driver</h1>

                <p className="register-subtitle">
                    Start earning by driving with Uber
                </p>

                <form onSubmit={handleSubmit}>

                    <div className="input-group">
                        <label>Full name</label>
                        <input
                            name="name"
                            placeholder="Enter your name"
                            value={form.name}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="input-group">
                        <label>Email</label>
                        <input
                            name="email"
                            type="email"
                            placeholder="Enter your email"
                            value={form.email}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="input-group">
                        <label>Password</label>
                        <input
                            name="password"
                            type="password"
                            placeholder="Create a password"
                            value={form.password}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="input-group">
                        <label>Vehicle number</label>
                        <input
                            name="vehicleNumber"
                            placeholder="e.g. MP09AB1234"
                            value={form.vehicleNumber}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="input-group">
                        <label>Vehicle model</label>
                        <input
                            name="vehicleModel"
                            placeholder="e.g. Honda City"
                            value={form.vehicleModel}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <button
                        type="submit"
                        className="register-button"
                    >
                        Create driver account
                    </button>

                </form>

                {message && (
                    <p className="register-message">
                        {message}
                    </p>
                )}

                <div className="register-divider">
                    <span>OR</span>
                </div>

                <p className="login-text">
                    Already have a driver account?{" "}
                    <span
                        className="login-link"
                        onClick={() => window.location.href = "/driver/login"}
                    >
                    Sign in
                </span>
                </p>

            </div>

        </div>
    );
}

export default DriverRegister;
