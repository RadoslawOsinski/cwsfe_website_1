Uruchamiać z przekazanym w zaleznosci od preferencji.

-Dspring.profiles.active="production_datasource_jndi"
lub
-Dspring.profiles.active="production_datasource_property"

=====================
Recreate schema:

DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;
COMMENT ON SCHEMA public IS 'standard public schema';