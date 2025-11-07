import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import PrivateRoute from "./components/admin/PrivateRoute";
//site
import IndexLayout from "./layouts/IndexLayout";
import Index from "./pages/site/Index";
import Contact from "./pages/site/Contact";
import Login from "./pages/Login";

//admin
import AdminLayout from "./layouts/AdminLayout";
import AdminIndex from "./pages/admin/Index";
import Lawyers from "./pages/admin/Lawyers";
import AddLawyer from "./pages/admin/AddLawyer";

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />}></Route>

        {/* Admin */}
        <Route element={<PrivateRoute />}>
          <Route path="/admin" element={<AdminLayout />}>
            <Route index element={<AdminIndex />}></Route>
            <Route path="lawyers" element={<Lawyers />}></Route>
            <Route path="lawyers/add" element={<AddLawyer />}></Route>
          </Route>
        </Route>

        {/* Site */}
        <Route path="/" element={<IndexLayout />}>
          <Route index element={<Index />}></Route>
          <Route path="iletisim" element={<Contact />}></Route>
        </Route>
      </Routes>
    </Router>
  )
}
