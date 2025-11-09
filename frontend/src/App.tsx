import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AdminRoute from "./components/admin/AdminRoute";
import LawyerRoute from "./components/admin/lawyer/LawyerRoute";

//site
import IndexLayout from "./layouts/IndexLayout";
import Index from "./pages/site/Index";
import Contact from "./pages/site/Contact";
import Login from "./pages/Login";
import RandevuAl from "./pages/site/Appointment";

//admin
import AdminLayout from "./layouts/AdminLayout";
import AdminIndex from "./pages/admin/Index";
import Lawyers from "./pages/admin/Lawyers";
import AddLawyer from "./pages/admin/AddLawyer";
import Faqs from "./pages/admin/Faqs";
import AddFaq from "./pages/admin/AddFaq";

//lawyer
import LawyerLayout from "./layouts/LawyerLayout";
import LawyerIndex from "./pages/admin/lawyer/Index";

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />}></Route>

        {/* Admin */}
        <Route element={<AdminRoute />}>
          <Route path="/admin" element={<AdminLayout />}>
            <Route index element={<AdminIndex />}></Route>
            <Route path="lawyers" element={<Lawyers />}></Route>
            <Route path="lawyers/add" element={<AddLawyer />}></Route>
            <Route path="faqs" element={<Faqs />}></Route>
            <Route path="faqs/add" element={<AddFaq />}></Route>
          </Route>
        </Route>

        {/* Lawyer */}
        <Route element={<LawyerRoute />}>
          <Route path="/lawyer" element={<LawyerLayout />}>
            <Route index element={<LawyerIndex />}></Route>
          </Route>
        </Route>

        {/* Site */}
        <Route path="/" element={<IndexLayout />}>
          <Route index element={<Index />}></Route>
          <Route path="iletisim" element={<Contact />}></Route>
          <Route path="randevu" element={<RandevuAl />}></Route>
        </Route>
      </Routes>
    </Router>
  )
}
