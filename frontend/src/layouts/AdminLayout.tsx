import { Outlet } from "react-router-dom";
import { Toaster } from "react-hot-toast";
import Sidebar from "../components/admin/Sidebar";

export default function AdminLayout() {
  return(
    <>
      <main className="flex">
        <Sidebar />
        <div className="w-full p-12 bg-gray-100">
          <Outlet />
        </div>
      </main>
      <Toaster position="top-center" toastOptions={{ duration: 3000 }}/>
    </>
  );
}