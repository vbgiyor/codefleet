module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
    "./node_modules/github-markdown-css/**/*.css"
  ],
  theme: {
    extend: {
      colors: {
        primaryBlue: '#1E40AF',
        lightBlue: '#DBEAFE',
        white: '#FFFFFF',
      },
    },
  },
  plugins: [],
};