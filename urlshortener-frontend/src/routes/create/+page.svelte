<script>
  import axios from "axios";
  import { page } from "$app/stores";
  import { user, jwt_token } from "../../store";


  const api_root =  $page.url.origin;
  let urlCreated = false;
  let shortUrl = null;

  let url = {
    longUrl: null,
    user: $user.name,
    description: null,
  };

  function createShortUrl() {
    var config = {
      method: "post",
      url: api_root + "/api/shorten",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer "+$jwt_token
      },
      data: url,
    };

    axios(config)
      .then(function (response) {
        urlCreated = true;
        shortUrl = response.data;
        url = {
          longUrl: null,
          user: $user.name,
          description: null,
        };
      })
      .catch(function (error) {
        alert("Could not create url");
        console.log(error);
      });
  }
</script>

<h1 class="mt-3">Create Short URL</h1>
<form class="mb-5">
  <div class="row mb-3">
    <div class="col">
      <label class="form-label" for="description">URL</label>
      <input
        bind:value={url.longUrl}
        class="form-control"
        id="longUrl"
        type="text"
      />
    </div>
  </div>
  <div class="row mb-3">
    <div class="col">
      <label class="form-label" for="description">Description</label>
      <input
        bind:value={url.description}
        class="form-control"
        id="description"
        type="text"
        maxlength="55"
      />
    </div>
  </div>
  <button type="button" class="btn btn-primary" on:click={createShortUrl}
    >Submit</button
  >
</form>
{#if urlCreated}
  <div id="url-created" class="alert alert-success alert-dismissible" role="alert">
    Short URL created: {api_root}/go/{shortUrl}
    <button type="button" class="btn-close" aria-label="Close" on:click={() => urlCreated = false}></button>
  </div>
{/if}

