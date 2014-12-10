#!/usr/bin/make

dev:
	@echo "Checking dev operations..."
	@if [ `which node` ]; then echo "nodejs... \033[32mOK\033[0m"; else echo "nodejs... \033[31mNOT ok\033[0m"; fi
	@if [ `which mocha` ]; then echo "mocha... \033[32mOK\033[0m"; else echo "mocha... \033[31mNOT ok\033[0m"; fi
	@node -p "var webdriver = require(\"selenium-webdriver\");\"\"";\
	if [ "$$?" -eq 0 ]; then echo "selenium-webdriver... \033[32mOK\033[0m"; else echo "selenium-webdriver for nodejs... \033[31mNOT ok\033[0m"; fi
	@node -p "var should = require(\"should\");\"\"";\
	if [ "$$?" -eq 0 ]; then echo "mocha should... \033[32mOK\033[0m"; else echo "mocha should... \033[31mNOT ok\033[0m"; fi
	@node -p "var blanket = require(\"blanket\")();\"\"" optional_argument;\
	if [ "$$?" -eq 0 ]; then echo "blanket... \033[32mOK\033[0m"; else echo "blanket... \033[31mNOT ok\033[0m"; fi

clean:
	rm captured_widgets/*.png

tests-unit:
	@echo "testing something cool..."
	@mocha --require should --reporter dot tests/unit

tests-functional:
	@mocha --require should --timeout 30000 tests/functional

coverage:
	@mocha --require should --reporter html-cov tests/unit > coverage.html; open coverage.html

.PHONY: dev tests clean
