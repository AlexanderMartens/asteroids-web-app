FROM ubuntu:latest
RUN apt-get update && apt-get install npm -y
COPY frontend ./frontend
COPY fix.sh ./fix.sh
WORKDIR /frontend/my-app
EXPOSE 5173

CMD ["/fix.sh"]
