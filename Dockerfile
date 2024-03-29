FROM python:3.9

WORKDIR /app


COPY requirements.txt .


RUN pip install -r requirements.txt

RUN  pip install --upgrade pip

COPY . .
EXPOSE 5000
CMD [ "python", "Main.py" ]