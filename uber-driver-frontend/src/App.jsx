import { BrowserRouter, Routes, Route } from "react-router-dom";

import DriverLogin from "./pages/DriverLogin";
import DriverRegister from "./pages/DriverRegister";
import DriverDashboard from "./pages/DriverDashboard";



import UserDashboard from "./pages/UserDashboard";
import UserLogin from "./pages/UserLogin";
import UserRegister from "./pages/UserRegister";

import "./App.css";

function App() {

    return (
        <BrowserRouter>

            <Routes>

                {/* USER */}

                <Route
                    path="/login"
                    element={<UserLogin />}
                />

                <Route
                    path="/register"
                    element={<UserRegister />}
                />

                <Route
                    path="/user/dashboard"
                    element={<UserDashboard />}
                />


                {/* DRIVER */}

                <Route
                    path="/driver/login"
                    element={<DriverLogin />}
                />

                <Route
                    path="/driver/register"
                    element={<DriverRegister />}
                />

                <Route
                    path="/dashboard"
                    element={<DriverDashboard />}
                />


                {/* DEFAULT */}

                <Route
                    path="/"
                    element={<UserLogin />}
                />

                <Route
                    path="*"
                    element={<UserLogin />}
                />

            </Routes>

        </BrowserRouter>
    );
}

export default App;