mkdocs:
	@docker build --no-cache -f Dockerfile.docs -t fiap-3soat-g15-tech-challenge-website:latest .

docs: mkdocs
	@docker run --rm -v ${CURDIR}:/usr/src/app fiap-3soat-g15-tech-challenge-website:latest build --clean

website: mkdocs
	@docker run --rm -v ${CURDIR}:/usr/src/app -p 8000:8000 fiap-3soat-g15-tech-challenge-website:latest serve

.PHONY: mkdocs docs website
