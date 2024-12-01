import session from '../helpers/auth/session.js'

export default function ClientList(props) {

    const clients = props.clients

    async function handleEditClient() {

    }

    async function handleDeleteClient() {

    }

    return <>
        {clients.map(client => (
            <div class="space-y-6 ml-10" key={client.userId}>
                <div class="flex justify-between py-6 border-b border-gray-200">
                    <div class="flex-1 space-y-2">
                        <p class="text-2xl text-blue-800">{client.username}</p>
                        <p class="text-xl font-semibold">{client.company}</p>
                        <p class="text-xl font-thin">{client.email}</p>
                        <p class="text-xl font-thin">Tel: {client.phone}</p>
                    </div>

                    <div className="flex flex-col items-end space-y-2">
                        {session.rol === "admin" && (
                            <button onClick={handleEditClient} className="flex items-center w-40 bg-blue-600 text-white py-2 px-4 rounded-lg">
                                    <i className="fas fa-pen-alt mr-7"></i>Editar</button>
                        )}

                        {session.rol === "admin" && (
                            <button onClick={handleDeleteClient} className="flex items-center w-40 bg-red-600 text-white py-2 px-4 rounded-lg">
                                    <i className="fas fa-times mr-7"></i>Eliminar</button>
                        )}
                    </div>
                </div>
            </div>
        ))}
    </>
}