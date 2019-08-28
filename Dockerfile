FROM airhacks/glassfish
COPY ./target/Homework.war ${DEPLOYMENT_DIR}
