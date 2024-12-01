import { Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import PrivateRoute from './components/PrivateRoute.jsx'
import { Error, Home, Login, Register } from './views/index.js'

export default function App() {
  
  return (
    <>
      <Router >
        <Routes>
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login /> } />
          <Route path="/home/*" element={<PrivateRoute> <Home /> </PrivateRoute>} />
          <Route path="/404" element={<Error />} />
        </Routes>
      </Router>
    </>
  )
}
