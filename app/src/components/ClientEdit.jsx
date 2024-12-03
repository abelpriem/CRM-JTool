import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { editClient, retrieveClientById } from '../logic/index.js'

export default function ClientEdit() {
    const [client, setClient] = useState({})
    const [showError, setShowError] = useState('')
    const [showMessage, setShowMessage] = useState('')

    const [name, setName] = useState()
    const [email, setEmail] = useState()
    const [company, setCompany] = useState()
    const [phone, setPhone] = useState()

    const navigate = useNavigate()
    const { clientId } = useParams()

    useEffect(() => {
        async function fetchClient() {
            try {
                const selectedClient = await retrieveClientById(clientId)
                setClient(selectedClient)
            } catch (error) {
                navigate("/404")
            }
        }

        fetchClient()
    }, [clientId])

    function handleNameChange(event) {
        setName(event.target.value)
    }

    function handleEmailChange(event) {
        setEmail(event.target.value)
    }

    function handleCompanyChange(event) {
        setCompany(event.target.value)
    }

    function handlePhoneChange(event) {
        setPhone(event.target.value)
    }

    async function handleSubmitEdit(event) {
        event.preventDefault()

        setShowError('')
        setShowMessage('')

        try {
            await editClient(clientId, name, email, company, phone)
            setShowMessage("Cliente editado correctamente!")
        } catch (error) {
            setShowError(error.message)
        }
    }

    return <>
        <h2 className='font-extrabold mt-4 my-5 text-2xl'>Editar cliente - ID: {clientId}</h2>

        {showError && (
            <div className="font-bold text-red-600">{showError}</div>
        )}

        {showMessage && (
            <div className="font-bold text-green-600">{showMessage}</div>
        )}

        <form onSubmit={handleSubmitEdit} className="flex flex-col space-y-3 w-80 p-3 mt-2">
            <label className="text-sm font-bold">Nombre Cliente:</label>
            <input className="p-2 border border-gray-300 rounded-md" type="text" name="name" placeholder={client.username} value={name} onChange={handleNameChange} required />

            <label className="text-sm font-bold">Email:</label>
            <input className="p-2 border border-gray-300 rounded-md" type="email" name="email" placeholder={client.email} value={email} onChange={handleEmailChange} required />

            <label className="text-sm font-bold">Empresa:</label>
            <input className="p-2 border border-gray-300 rounded-md" type="text" name="company" placeholder={client.company} value={company} onChange={handleCompanyChange} required />

            <label className="text-sm font-bold">Teléfono:</label>
            <input className="p-2 border border-gray-300 rounded-md" type="text" name="phone" placeholder={client.phone} value={phone} onChange={handlePhoneChange} required />

            <button className="bg-green-600 text-white py-2 px-4 rounded-md hover:bg-green-800" type="submit" value="Save client">Guardar cambios</button>
        </form>

    </>
}