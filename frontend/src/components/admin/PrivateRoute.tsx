import { Navigate, Outlet } from "react-router-dom";

export default function PrivateRoute() {
  const token = localStorage.getItem("token");
  const expiresAt = localStorage.getItem("expiresAt");

  const isExpired = expiresAt ? new Date(expiresAt) < new Date() : true;

  if (!token || isExpired) {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("expiresAt");
    return <Navigate to="/login" replace />;
  }

  return <Outlet />;
}
