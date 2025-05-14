FROM python:3.11-slim
WORKDIR /app
COPY backend/requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
COPY backend/ .
COPY frontend/build ./frontend/build
EXPOSE 8000
CMD ["gunicorn", "--bind", "0.0.0.0:8000", "codefleet.wsgi:application"]

# FROM python:3.11-slim

# Install Node.js and npm for React build
# RUN apt-get update && apt-get install -y \
#     nodejs \
#     npm \
#     && npm install -g npm@latest \
#     && rm -rf /var/lib/apt/lists/*

# WORKDIR /app
# COPY backend/requirements.txt .
# RUN pip install --no-cache-dir -r requirements.txt
# COPY backend/ .

# # Copy the entrypoint script
# COPY backend/entrypoint.sh /app/entrypoint.sh
# RUN chmod +x /app/entrypoint.sh
# COPY frontend/build ./frontend/build
# EXPOSE 8000
# CMD ["/app/entrypoint.sh"]