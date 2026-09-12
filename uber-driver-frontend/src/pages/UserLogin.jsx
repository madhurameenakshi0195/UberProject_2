import { useState } from "react";
import { userLogin } from "../services/api";
import { useNavigate } from "react-router-dom";

function UserLogin() {

    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    async function handleSubmit(event) {

        event.preventDefault();

        try {

            const data = await userLogin(
                email,
                password
            );

            // Save user JWT
            localStorage.setItem(
                "userToken",
                data.token
            );

            // Save user information
            localStorage.setItem(
                "userId",
                data.userId
            );

            localStorage.setItem(
                "userName",
                data.name
            );

            localStorage.setItem(
                "userEmail",
                data.email
            );

            navigate("/user/dashboard");

        } catch (error) {

            alert("Login failed");
        }
    }

    return (
        <div className="user-login-page">

            <div className="user-login-card">

                <div className="user-logo">
                    UBER
                </div>

                <h1>Welcome back</h1>

                <p className="user-subtitle">
                    Sign in to request a ride
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
                        className="user-login-button"
                    >
                        Sign in
                    </button>

                </form>


                <p className="user-register-text">

                    New to Uber?

                    <button
                        type="button"
                        className="user-register-link"
                        onClick={() =>
                            navigate("/register")
                        }
                    >
                        Create an account
                    </button>

                </p>


                <div className="driver-option">

                    <span>
                        Are you a driver?
                    </span>

                    <button
                        type="button"
                        onClick={() =>
                            navigate("/driver/login")
                        }
                    >
                        Driver login
                    </button>

                </div>

            </div>

        </div>
    );
}

export default UserLogin;