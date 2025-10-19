import { Outlet } from "react-router-dom";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";

export default function IndexLayout() {
  return(
    <>
      <Navbar />
      <main className="p-6 lg:p-24">
        <Outlet />
      </main>
      <Footer />
    </>
  );
}