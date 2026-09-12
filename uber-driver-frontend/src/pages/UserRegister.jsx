import { useState } from "react";
import { userRegister } from "../services/api";
import { useNavigate } from "react-router-dom";

function UserRegister() {

    const navigate = useNavigate();

    const [form, setForm] = useState({
        name: "",
        email: "",
        password: "",
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

            await userRegister(form);

            setMessage("Account created successfully!");

            setTimeout(() => {
                navigate("/login");
            }, 1000);

        } catch (error) {

            setMessage(error.message);
        }
    }

    return (
        <div className="user-register-page">

            <div className="user-register-card">

                <div className="user-logo">
                    UBER
                </div>

                <h1>Create your account</h1>

                <p className="user-subtitle">
                    Sign up to start riding
                </p>

                <form onSubmit={handleSubmit}>

                    <div className="input-group">

                        <label>Name</label>

                        <input
                            type="text"
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
                            type="email"
                            name="email"
                            placeholder="Enter your email"
                            value={form.email}
                            onChange={handleChange}
                            required
                        />

                    </div>


                    <div className="input-group">

                        <label>Password</label>

                        <input
                            type="password"
                            name="password"
                            placeholder="Create a password"
                            value={form.password}
                            onChange={handleChange}
                            required
                        />

                    </div>


                    <button
                        type="submit"
                        className="user-register-button"
                    >
                        Create account
                    </button>

                </form>


                {message && (
                    <p className="user-message">
                        {message}
                    </p>
                )}


                <p className="user-login-text">

                    Already have an account?

                    <button
                        type="button"
                        className="user-login-link"
                        onClick={() => navigate("/login")}
                    >
                        Sign in
                    </button>

                </p>

            </div>

        </div>
    );
}

export default UserRegister;