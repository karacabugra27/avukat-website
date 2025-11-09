import { Outlet } from "react-router-dom";
import Navbar from "../components/site/Navbar";
import Footer from "../components/site/Footer";
import { Toaster } from "react-hot-toast";
export default function IndexLayout() {
  return(
    <>
      <Navbar />
      <main>
        <Outlet />
      </main>
      <Footer />
      <Toaster position="top-center" toastOptions={{duration: 3000}}/>
    </>
  );
}