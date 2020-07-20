module.exports = {
  "devServer": {
    port: 8098,
    "proxy": {
      "/api": {
        "target": "http://localhost:8080",
        "ws": true,
        "changeOrigin": true
      }
    }
  },
  "outputDir": "target/dist",
  "assetsDir": "static",
  "transpileDependencies": [
    "vuetify"
  ]
}