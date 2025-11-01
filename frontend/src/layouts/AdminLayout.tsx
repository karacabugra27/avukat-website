import { Outlet } from "react-router-dom";
import Sidebar from "../components/admin/Sidebar";

export default function AdminLayout() {
  return(
    <>
      <main className="flex">
        <Sidebar />
        <div className="p-12">
          <Outlet />
        </div>
      </main>
    </>
  );
}