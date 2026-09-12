import { useState } from "react";
import { driverLogin } from "../services/api";
import { useNavigate } from "react-router-dom";

function DriverLogin() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    async function handleSubmit(event) {

        event.preventDefault();

        try {

            const data = await driverLogin(
                email,
                password
            );

            localStorage.setItem(
                "driverToken",
                data.token
            );

            localStorage.setItem(
                "driverId",
                data.driverId
            );

            localStorage.setItem(
                "driverName",
                data.name
            );

            localStorage.setItem(
                "vehicleNumber",
                data.vehicleNumber
            );

            localStorage.setItem(
                "vehicleModel",
                data.vehicleModel
            );

            localStorage.setItem(
                "driverAvailable",
                data.available
            );

            navigate("/dashboard");

        } catch (error) {

            alert("Login failed");

        }
    }

    return (
        <div className="driver-login-page">

            <div className="driver-login-card">

                <div className="login-logo">
                    UBER
                </div>

                <h1>Welcome back</h1>

                <p className="login-subtitle">
                    Sign in to your driver account
                </p>

                <form onSubmit={handleSubmit}>

                    <div className="input-group">
                        <label>Email</label>

                        <input
                            type="email"
                            placeholder="Enter your email"
                            value={email}
                            onChange={(e) =>
                                setEmail(e.target.value)
                            }
                            required
                        />
                    </div>

                    <div className="input-group">
                        <label>Password</label>

                        <input
                            type="password"
                            placeholder="Enter your password"
                            value={password}
                            onChange={(e) =>
                                setPassword(e.target.value)
                            }
                            required
                        />
                    </div>

                    <button
                        type="submit"
                        className="login-button"
                    >
                        Sign in
                    </button>

                </form>

                <div className="login-divider">
                    <span>OR</span>
                </div>

                <p className="register-text">
                    New driver?
                    <button
                        type="button"
                        className="register-link"
                        onClick={() =>
                            navigate("/driver/register")
                        }
                    >
                        Create an account
                    </button>
                </p>

            </div>

        </div>
    );
}

export default DriverLogin;