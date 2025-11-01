import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import IndexLayout from "./layouts/IndexLayout";
import Index from "./pages/site/Index";
import Contact from "./pages/site/Contact";
import Login from "./pages/Login";
import AdminLayout from "./layouts/AdminLayout";
import AdminIndex from "./pages/admin/Index";

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />}></Route>
        <Route path="/admin" element={<AdminLayout />}>
          <Route index element={<AdminIndex />}></Route>
        </Route>
        <Route path="/" element={<IndexLayout />}>
          <Route index element={<Index />}></Route>
          <Route path="iletisim" element={<Contact />}></Route>
        </Route>
      </Routes>
    </Router>
  )
}
