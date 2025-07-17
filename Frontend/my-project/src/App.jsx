import React, { useState , useEffect } from "react";
import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import Login from "./components/Login";
import Signup from "./components/Signup";
import Dashboard from "./components/Teacher/Dashboard";
import HOD_Dashboard from "./components/HOD/HOD_Dashboard";
import PrivateRoute from "./components/PrivateRoute";
import ResetPassword from "./components/ResetPassword";
import { refresh_token_api } from "./api/auth_api"; // Import the refresh_token_api function

function App() {
  const [l_token, setLToken] = useState(localStorage.getItem("l_token") || "");

  //  Keep token in sync with localStorage
  useEffect(() => {
    const handleStorageChange = () => {
      setLToken(localStorage.getItem("l_token") || "");
    };

    window.addEventListener("storage", handleStorageChange);
    return () => window.removeEventListener("storage", handleStorageChange);
  }, []);

  // Refresh token every 9 minutes
  useEffect(() => {
    const interval = setInterval(() => {
      refresh_token_api()
        .then(response => {
          if (response && response.data && response.data.accessToken) {
            const newToken = response.data.accessToken;
            localStorage.setItem("l_token", newToken);
            setLToken(newToken);
            console.log("Token refreshed:", newToken);
          }
        })
        .catch(error => {
          console.error("Error refreshing token:", error);
        });
    }, 9 * 60 * 1000); // 9 minutes in milliseconds

    return () => clearInterval(interval); // Cleanup interval on component unmount
  }, []);

  return (
    <Router>
      <Routes>
        <Route path="/login" element={l_token ? <Navigate to="/dashboard" replace /> : <Login setLToken={setLToken} />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/reset_password" element={<ResetPassword/>} />
        <Route path="/dashboard/*"  element={l_token? <Dashboard setLToken={setLToken} /> :  <Navigate to="/login" replace />} />
        <Route path="/hod_dashboard/*" element={l_token ?  <HOD_Dashboard setLToken={setLToken}/>:<Navigate to="/login" replace />} />
        <Route path="*" element={<Login setLToken={setLToken} />} />
      </Routes>
    </Router>
  );
}

export default App;
