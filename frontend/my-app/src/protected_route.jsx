import { Navigate } from 'react-router-dom';
import useAuth from './auth_context';

// defines the wrapper component for protected routes
const ProtectedRoute = ({children}) => {

    const user = useAuth();

    // if user is null, that means nobody is logged in
    if (!user){

        // Reroute to login page
        // Replace tells the router not to leave a button trail
        // Meaning, the user cannot click <- and return to the protected page
        return <Navigate to='/' replace />
    }

    // Show requested route/component
    return children;

}

// Usable in external files
export default ProtectedRoute;