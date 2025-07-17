import { Navigate, Outlet, useLocation } from "react-router-dom";

const PrivateRoute = () => {
  const l_token = localStorage.getItem("l_token");
  const location = useLocation();

  console.log("Private Route");
  console.log("Private token" , l_token);

  if (!l_token && location.pathname !== "/login") {
    return <Navigate to="/login" replace />;
  }

  return <Outlet />;
};

export default PrivateRoute;
