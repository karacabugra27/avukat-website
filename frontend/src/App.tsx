import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import IndexLayout from "./layouts/IndexLayout";
import Index from "./pages/Index";
import Contact from "./pages/Contact";
function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<IndexLayout />}>
          <Route index element={<Index />}></Route>
          <Route path="iletisim" element={<Contact />}></Route>
        </Route>
      </Routes>
    </Router>
  )
}

export default App
