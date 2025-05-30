const { createProxyMiddleware } = require('http-proxy-middleware');

console.log("Proxy setup initialized");

module.exports = function (app) {
    console.log("Proxy setup initialized");
    app.use(
        '/static/markdown',
        createProxyMiddleware({
            target: 'http://localhost:8000',
            changeOrigin: true,
            pathRewrite: { '^/static/markdown': '/static/markdown/md_to_html' },
            onProxyReq: (proxyReq, req) => {
                console.log(`Proxying: ${req.method} ${req.url} to ${proxyReq.getHeader('host')}${proxyReq.path}`);
            },
            onProxyRes: (proxyRes, req) => {
                console.log(`Response for ${req.url}: Status ${proxyRes.statusCode}`);
            },
            onError: (err, req, res) => {
                console.error(`Proxy error for ${req.url}: ${err.message}`);
                res.status(500).send(`Proxy error: ${err.message}`);
            }
        })
    );
    app.use(
        '/markdown',
        createProxyMiddleware({
        target: 'http://localhost:8000',
        changeOrigin: true,
        onProxyReq: (proxyReq, req) => {
            console.log(`Proxying: ${req.method} ${req.url} to ${proxyReq.getHeader('host')}${proxyReq.path}`);
        },
        onProxyRes: (proxyRes, req) => {
            console.log(`Response for ${req.url}: Status ${proxyRes.statusCode}`);
        },
        onError: (err, req, res) => {
            console.error(`Proxy error for ${req.url}: ${err.message}`);
            res.status(500).send(`Proxy error: ${err.message}`);
        },
        })
    );
};