import React, { useState ,useEffect} from "react";
import { Link, useNavigate } from "react-router-dom";
// import { AuthContext } from "../context/AuthContext";


const Navbar = ({setLToken}) => {

  // const {token , setToken} = useContext(AuthContext);
  const navigate = useNavigate();
  const l_token = localStorage.getItem("l_token");
  const isAuthenticated = l_token

  const handleLogout = () => {
    localStorage.removeItem("l_token");
    console.log("Token on logout");
    const token = localStorage.getItem('l_token');
    setLToken(token);
    navigate("/login");
  };

  return (
    <nav className="navbar py-2 position-sticky">
      <div className="navbar-brand d-flex gap-1 justify-content-center align-items-center">
        <img src="/Images/logo.png" className="img-fluid" alt="Logo" style={{ maxWidth: "40px" ,height:"40px"}} />
        EduLeave 
      </div>
      <div className="navbar-links">
        {isAuthenticated ? (
          <button onClick={handleLogout} className="btn btn-secondary text-decoration-none logout-btn">Logout</button>
        ) : (
          <Link to="/login " className="btn btn-secondary text-decoration-none">Login</Link>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
