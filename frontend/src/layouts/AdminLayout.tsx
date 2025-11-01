import { Outlet } from "react-router-dom";
import Sidebar from "../components/admin/Sidebar";

export default function AdminLayout() {
  return(
    <>
      <Sidebar />
      <main>
        <Outlet />
      </main>
    </>
  );
}