FROM python:3.11-slim
WORKDIR /app
COPY backend/requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
RUN mkdir -p /app/static/markdown
EXPOSE 8000
CMD ["gunicorn", "--bind", "0.0.0.0:8000", "codefleet.wsgi:application", "--preload", "--access-logfile", "-", "--error-logfile", "-"]