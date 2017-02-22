A wannabe Spring Boot MVC Framework. Created for educational purposes.

The idea is to build on top of the HttpServlets in JavaEE and create MVC framework. It should support the following functionality:
- Controllers - Anotated with ```@Controller```
- Handling multiple Post and Get Http requests using ```@GetMapping``` and ```@PostMapping```
- Handling dynamic URLs like "test.com/users/3/profile" using ```@PathVariable```
- Handling complex classes as parameters of actions using ```@ModelAttribute```
- Accessing request parameters using ```@RequestParam```
- Having Dependency Injection using ```@Autowired```

Usage:
- All controllers should have the @Controller annotation
- Dynamic parameters in the URL can be assigned through @GetMapping("/users/{id}/profile") and accessed through @PathVariable("id")
```java
	@GetMapping("users/{id}/profile")
	public String profile(@PathVariable("id") Integer id) { ... }
```
- Dynamic parameters in the URL can be assigned through @PostMapping("/users/{id}/profile"). See the example above.
- You can pass attributes to JSP using the ```Model``` class
```java
	@GetMapping("users/{id}/profile")
	public String profile(@PathVariable("id") Integer id, Model model) {
    model.addAttribute("user", this.userRepository.get(id));
    ...
  }
```

More will come later...
