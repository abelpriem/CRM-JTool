import { Navigate, Route, Routes } from 'react-router-dom'
import { ClientEdit, Clients, Header, Navigation, NewClient, Orders, Products } from '../components/index.js'

export default function Home() {
    return <>
        <div className="bg-gray-200 min-h-screen">
            <Header />

            <div className="flex">
                <Navigation />

                <div className="bg-white rounded ml-40 mt-8 mr-32 mb-8 p-5 flex-1 min-h-[calc(100vh-4rem)]">
                    <Routes>
                        <Route path="/" element={<Navigate to="clients" />} />
                        <Route path="clients" element={<Clients />} />
                        <Route path="products" element={<Products />} />
                        <Route path="orders" element={<Orders />} />
                        <Route path="clients/edit/*" element={<ClientEdit />} />
                        <Route path="clients/new-client" element={<NewClient />} />
                    </Routes>
                </div>
            </div>
        </div>
    </>
}