<script>
  import { isAuthenticated } from "../store";
  import auth from "../auth.service";

  let username = "";
  let password = "";
  let loginForm;

  function loginWithUsernameAndPassword() {
    // form validation with bootstrap: see https://getbootstrap.com/docs/5.3/forms/validation/
    if (loginForm.checkValidity()) {
      console.log("login");
      auth.login(username, password);
    }
    loginForm.classList.add("was-validated");
  }
</script>

{#if $isAuthenticated}
  <div class="row">
    <div class="col-sm-6">
      <div class="card text-center" style="width: 18rem; background-color: rgba(0, 123, 255, 0.5);">
        <div class="card-body">
          <h5 class="card-title">Create a New Short URL</h5>
          <p class="card-text">Generate a short URL for your long links.</p>
          <a href="/create" class="btn btn-primary">Create</a>
        </div>
      </div>
    </div>
    <div class="col-sm-6">
      <div class="card text-center" style="width: 18rem; background-color: rgba(0, 123, 255, 0.5);">
        <div class="card-body">
          <h5 class="card-title">Manage Existing URLs</h5>
          <p class="card-text">View and manage your previously created short URLs.</p>
          <a href="/manage" class="btn btn-primary">Manage</a>
        </div>
      </div>
    </div>
  </div>
{:else}
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card">
          <div class="card-header">Login</div>
          <div class="card-body">
            <form
              on:submit|preventDefault={loginWithUsernameAndPassword}
              bind:this={loginForm}
              class="needs-validation"
              novalidate
            >
              <div class="mb-3">
                <label for="username" class="form-label">Email</label>
                <input
                  bind:value={username}
                  type="text"
                  class="form-control"
                  id="username"
                  name="username"
                  required
                />
                <div class="invalid-feedback">
                  Please provide your username.
                </div>
              </div>
              <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input
                  bind:value={password}
                  type="password"
                  class="form-control"
                  id="password"
                  name="password"
                  required
                />
                <div class="invalid-feedback">
                  Please provide your password.
                </div>
              </div>
              <div class="row align-items-end">
                <div class="col">
                  <button type="submit" class="btn btn-primary">Log in</button>
                </div>
                <div class="col-auto">
                  <a href="/signup">Sign up</a>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
{/if}
