(defproject fury "0.0.1-SNAPSHOT"
  :description "Elasticsearch cloud automation"
  :dependencies [[com.palletops/pallet "0.8.0-RC.9"]
                 [com.palletops/pallet-jclouds "1.7.3"]
                 [org.apache.jclouds/jclouds-allblobstore "1.7.2"]
                 [org.apache.jclouds/jclouds-allcompute "1.7.2"]
                 [org.apache.jclouds.driver/jclouds-slf4j "1.7.2"
                  :exclusions [org.slf4j/slf4j-api]]
                 [org.apache.jclouds.driver/jclouds-sshj "1.7.2"
                  :exclusions [net.schmizz/sshj]]
                 [ch.qos.logback/logback-classic "1.0.9"]

                 [com.palletops/pallet-vmfest "0.4.0-alpha.1"]
                 [org.clojars.tbatchelli/vboxjws "4.3.4"]

                 [org.clojure/tools.cli "0.3.1"]]
  :profiles {:dev
             {:dependencies
              [[com.palletops/pallet "0.8.0-RC.9"
                :classifier "tests"]
               [org.clojure/clojure "1.6.0"]]}
             :leiningen/reply
             {:dependencies [[org.slf4j/jcl-over-slf4j "1.7.2"]]
              :exclusions [commons-logging]}}
  :local-repo-classpath true
  :repositories {"sonatype"
                 "https://oss.sonatype.org/content/repositories/releases/"}
  :aot :all
  :main fury.main
  :jvm-opts ["-Djava.awt.headless=true"]
)
