import { Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import { PrivateRoute } from './components/index.js'
import { Error, Home, Login, Profile, Register } from './views/index.js'

export default function App() {

  return (
    <>
      <Router >
        <Routes>
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route path="/404" element={<Error />} />
          <Route path="/home/*" element={<PrivateRoute> <Home /> </PrivateRoute>} />
          <Route path="/profile" element={<PrivateRoute> <Profile /> </PrivateRoute>} />
        </Routes>
      </Router>
    </>
  )
}
