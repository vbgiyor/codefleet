FROM python:3.11-slim

WORKDIR /app

ENV PYTHONPATH=/app

COPY bookslab/initial_data.json bookslab/

COPY ./requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY ./ .

# Make scripts executable
RUN chmod +x entrypoint.sh

ENTRYPOINT ["sh", "./entrypoint.sh"]