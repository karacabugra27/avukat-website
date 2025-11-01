import { Outlet } from "react-router-dom";
import Navbar from "../components/site/Navbar";
import Footer from "../components/site/Footer";

export default function IndexLayout() {
  return(
    <>
      <Navbar />
      <main>
        <Outlet />
      </main>
      <Footer />
    </>
  );
}