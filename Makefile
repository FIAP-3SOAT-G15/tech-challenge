mkdocs:
	@docker build --no-cache -f Dockerfile.docs -t mkdocs-fiap-3soat-g15-tech-challenge:latest .

cm:
	@docker build --no-cache -f Dockerfile.cm -t cm-fiap-3soat-g15-tech-challenge:latest .

docs: mkdocs
	@docker run --rm -v ${CURDIR}:/usr/src/app mkdocs-fiap-3soat-g15-tech-challenge:latest build --clean

website: mkdocs
	@docker run --rm -v ${CURDIR}:/usr/src/app -p 8000:8000 mkdocs-fiap-3soat-g15-tech-challenge:latest serve

map: cm
	@docker run --rm -v ${CURDIR}:/app cm-fiap-3soat-g15-tech-challenge:latest generate -i ./docs/cml/context-map.cml -g context-map -o ./docs/diagrams

.PHONY: mkdocs docs website
