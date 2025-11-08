import { Navigate, Outlet } from "react-router-dom";

export default function AdminRoute() {
  const token = localStorage.getItem("token");
  const expiresAt = localStorage.getItem("expiresAt");
  const role = localStorage.getItem("role");

  const isExpired = expiresAt ? new Date(expiresAt) < new Date() : true;

  if (!token || isExpired) {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("expiresAt");
    return <Navigate to="/login" replace />;
  }

  if(role !== "SUPER_ADMIN") {
    return <Navigate to="/login" replace />;
  }

  return <Outlet />;
}
