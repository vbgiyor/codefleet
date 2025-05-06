FROM python:3.9-slim

WORKDIR /app

COPY requirements/base.txt requirements/
RUN pip install --no-cache-dir -r requirements/base.txt

COPY core/ core/
COPY apps/djangocommerce/ apps/djangocommerce/

ENV DJANGO_SETTINGS_MODULE=core.settings.base
ENV PYTHONPATH=/app

# RUN python apps/djangocommerce/manage.py collectstatic --noinput --settings=core.settings.base

CMD ["gunicorn", "--bind", "0.0.0.0:8000", "core.wsgi:application"]