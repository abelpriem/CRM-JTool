

export default function Navigation() {
    return <>
        <aside className="sidebar col-3 pt-16">
            <h2 className="text-xl font-bold mb-6 pl-20 underline">Administración</h2>

            <nav className="felx flex-col text-gray-700">
                <div className="flex pl-20 pt-8 space-x-2">
                    <img src='/clients.png' alt="Icono de Clientes" className="w-8 h-8"/>
                    <a href="/home/clients" className="relative font-[var(--fontTexto)] text-[var(--grisOscuro)] text-2xl hover:text-gray-900">Clientes</a>
                </div>

                <div className="flex pl-20 pt-8 space-x-2">
                    <img src='/products.png' alt="Icono de Productos" className="w-8 h-8"/>
                    <a href="/home/products" className="relative font-[var(--fontTexto)] text-[var(--grisOscuro)] text-2xl hover:text-gray-900">Productos</a>
                </div>

                <div className="flex pl-20 pt-8 space-x-2">
                    <img src='/orders.png' alt="Icono de Pedidos" className="w-8 h-8"/>
                    <a href="/home/orders" className="relative font-[var(--fontTexto)] text-[var(--grisOscuro)] text-2xl hover:text-gray-900">Pedidos</a>
                </div>
            </nav>
        </aside>
    </>
}