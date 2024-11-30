
export default function Client() {
    return <>
        <div class="space-y-6 ml-10">
            <div class="flex justify-between py-6 border-b border-gray-200">
                <div class="flex-1 space-y-2">
                    <p class="text-2xl text-blue-800">Cliente Nombre</p>
                    <p class="text-xl font-semibold">Empresa ABC</p>
                    <p class="text-xl font-thin">correo@correo.com</p>
                    <p class="text-xl font-thin">Tel: 690123456</p>
                </div>

                <div className="flex flex-col items-end space-y-2">
                    <a href="#" className="flex items-center w-40 bg-blue-600 text-white py-2 px-4 rounded-lg">
                            <i className="fas fa-pen-alt mr-7"></i>Editar</a>
                    <a href="#" className="flex items-center w-40 bg-red-600 text-white py-2 px-4 rounded-lg">
                            <i className="fas fa-times mr-7"></i>Eliminar</a>
                </div>
            </div>
        </div>
    </>
}