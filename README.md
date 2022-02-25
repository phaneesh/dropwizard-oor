# Dropwizard Oor Bundle [![Travis build status](https://travis-ci.org/phaneesh/dropwizard-oor.svg?branch=master)](https://travis-ci.org/phaneesh/dropwizard-oor)

This bundle adds a healthcheck which can used to take the application out of rotation from
a loadbalancer which uses /healthcheck endpoint for healthchecks.
Also gives capability to expose admin port /healthcheck on application port for lb's which poll on application port.
This bundle compiles only on Java 11.
 
## Usage
This makes it easier perform rolling deployments & maintenance of dropwizard applications
 
### Build instructions
  - Clone the source:

        git clone github.com/phaneesh/dropwizard-oor

  - Build

        mvn install

### Maven Dependency
* Use the following maven dependency:
```
<dependency>
    <groupId>io.dropwizard.oor</groupId>
    <artifactId>dropwizard-oor</artifactId>
    <version>2.0.24-2</version>
</dependency>
```

### Using Oor bundle

#### Bootstrap
```java
    @Override
    public void initialize(final Bootstrap...) {
        bootstrap.addBundle(new OorBundle() {
            
            @Override
            public boolean withOor() {
                return false;
            }
            
            //Use this only if you require healthcheck on application port
            @Override
            public boolean exposeApplicationPortHealthCheck() {
                return true;
            }

            
        });
    }
```

#### Tasks
* ```curl -XPOST http://host:adminport/tasks/oor``` for taking node out of rotation
* ```curl -XPOST http://host:adminport/tasks/bir``` for bringing node back into rotation

## Note
Please allow the node to run for the grace period and a bit more that
you have configured in your load balancer healthchecks so that the
load balancer can drain all the requests gracefully.