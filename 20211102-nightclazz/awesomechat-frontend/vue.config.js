// TODO-07 : Review this file
module.exports = {
    devServer: {
        port: 8088,
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                ws: true,
                changeOrigin: true
            }
        }
    }
}
