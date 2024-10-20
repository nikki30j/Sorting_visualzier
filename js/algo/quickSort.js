async function quickSort() {
    await quickSortTest(0, arr_size - 1);
    await updateAllBarsToGreen();
    console.log('sortDone');
    stopProcess = false;
    enable_buttons();
  }
  
  async function updateAllBarsToGreen() {
    for (var i = 0; i < arr_size; i++) {
      await div_update_delay(divs[i], div_sizes[i], "green");
    }
  }
  
  async function quickSortTest(low, high) {
    if (stopProcess) return;
    if (low < high) {
      var pi = await partition(low, high);
      await quickSortTest(low, pi - 1);
      await quickSortTest(pi + 1, high);
    }
  }
  
  async function partition(low, high) {
    var pivot = div_sizes[high];
    var i = low - 1;
  
    for (var j = low; j < high; j++) {
      if (stopProcess) break;
      await div_update_delay(divs[j], div_sizes[j], "yellow");
      if (div_sizes[j] < pivot) {
        i++;
        await div_update_delay(divs[i], div_sizes[i], "red");
        div_update(divs[j], div_sizes[j], "red"); 
        var temp = div_sizes[i];
        div_sizes[i] = div_sizes[j];
        div_sizes[j] = temp;
        await div_update_delay(divs[i], div_sizes[i], "red");
        await div_update_delay(divs[j], div_sizes[j], "red");
      }
      await div_update_delay(divs[j], div_sizes[j], "blue"); 
    }
    await div_update_delay(divs[i + 1], div_sizes[i + 1], "green");
    div_update(divs[high], div_sizes[high], "green");
    var temp = div_sizes[i + 1];
    div_sizes[i + 1] = div_sizes[high];
    div_sizes[high] = temp;
    await div_update_delay(divs[i + 1], div_sizes[i + 1], "green"); 
    await div_update_delay(divs[high], div_sizes[high], "green");
    return i + 1;
  }