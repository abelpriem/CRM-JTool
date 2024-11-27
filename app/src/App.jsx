import { Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import { Home, Login, Register } from './views/index.js'

export default function App() {
  
  return (
    <>
      <Router >
        <Routes>
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login /> } />
          <Route path="/home/*" element={<Home />} />
        </Routes>
      </Router>
    </>
  )
}
