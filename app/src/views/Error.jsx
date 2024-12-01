import { Header } from '../components/index.js'

export default function Error() {
    return<>
        <div className="bg-gray-200 min-h-screen">
            <Header />

            <div className="bg-white rounded ml-40 mt-8 mr-32 mb-8 p-5 flex-1 min-h-[calc(100vh-4rem)]">
                <h1 className="text-center font-extrabold text-6xl mt-20">404 Not Found</h1>
                <p className="text-center font-thin text-3xl">Ups.. no hemos encontrado la página. Inténtelo de nuevo</p>
            </div>
        </div>
    </>
}