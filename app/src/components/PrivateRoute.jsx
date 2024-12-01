import { Navigate } from 'react-router-dom'
import session from '../helpers/auth/session.js'

export default function PrivateRoute({ children }) {
    return session.token ? children : <Navigate to={"/login"} />
}