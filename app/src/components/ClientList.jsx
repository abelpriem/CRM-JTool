import { useNavigate } from 'react-router-dom'
import Swal from 'sweetalert2'
import session from '../helpers/auth/session.js'
import { deleteClient } from '../logic/index.js'

export default function ClientList(props) {

    const clients = props.clients
    const setClients = props.setClients
    const navigate = useNavigate()

    function handleDeleteClient(clientId) {
        try {
            Swal.fire({
                title: "¿Desea eliminar el cliente seleccionado?",
                text: "No se podrán revertir los cambios...",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Eliminar"
            }).then(async (result) => {
                if (result.isConfirmed) {
                    try {
                        await deleteClient(clientId)
                        setClients(prevClients=> prevClients.filter(client => client.userId !== clientId))
    
                        Swal.fire({
                            title: "Eliminado",
                            text: "¡El cliente ha sido eliminado con éxito!",
                            icon: "success"
                        })
    
                        window.scrollTo({
                            top: 0,
                            behavior: 'smooth'
                        })
    
                    } catch (error) {
                        Swal.fire({
                            title: "Error",
                            text: "Hubo un problema al eliminar el cliente. Por favor, vuelva a intentarlo",
                            icon: "error"
                        })
                    }
                }
            })
        } catch(error) {
            navigate("/404")
        }
    }

    return <>
        {clients.map(client => (
            <div className="space-y-6 ml-10" key={client.userId}>
                <div className="flex justify-between py-6 border-b border-gray-200">
                    <div className="flex-1 space-y-2">
                        <p className="text-2xl text-blue-800">{client.username}</p>
                        <p className="text-xl font-semibold">{client.company}</p>
                        <p className="text-xl font-thin">{client.email}</p>
                        <p className="text-xl font-thin">Tel: {client.phone}</p>
                    </div>

                    <div className="flex flex-col items-end space-y-2">
                        {session.rol === "admin" && (
                            <a href={`/home/clients/edit/${client.userId}`} className="flex items-center w-40 bg-blue-600 text-white py-2 px-4 rounded-lg">
                                    <i className="fas fa-pen-alt mr-7"></i>Editar</a>
                        )}

                        {session.rol === "admin" && (
                            <button onClick={() => handleDeleteClient(client.userId)} className="flex items-center w-40 bg-red-600 text-white py-2 px-4 rounded-lg">
                                    <i className="fas fa-times mr-7"></i>Eliminar</button>
                        )}
                    </div>
                </div>
            </div>
        ))}
    </>
}